package com.sb.ims.services;
import com.sb.ims.model.Product;
import com.sb.ims.repository.ProductRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JavaMailSender mailSender;

    // Schedule this task to run every day at 5:5 PM
    @Scheduled(cron = "0 0 6 * * ?")
    public void generateReport() {
        try {
            // Generate CSV file
            File csvFile = generateCSV();

            // Generate PDF file
//            File pdfFile = generatePDF();

            // Send email with attachments
            sendEmailWithAttachments(csvFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File generateCSV() throws IOException {
        List<Product> products = productRepository.findAll();
        String csvFileName = "product_report_" + LocalDate.now() + ".csv";
        Path csvPath = Paths.get(csvFileName);

        try (Writer writer = Files.newBufferedWriter(csvPath);
             ICsvBeanWriter csvWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE)) {

            // Define the header
            String[] header = {"id", "name", "stockQuantity", "price"};
            csvWriter.writeHeader(header);

            // Write product data
            for (Product product : products) {
                csvWriter.write(product, header);
            }
        }

        return csvPath.toFile();
    }


    private void sendEmailWithAttachments(File csvFile) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo("admin@gmail.com");
        helper.setSubject("Daily Product and Stock Report");
        helper.setText("Please find attached the daily product and stock report.");

        helper.addAttachment(csvFile.getName(), csvFile);
//        helper.addAttachment(pdfFile.getName(), pdfFile);

        mailSender.send(message);
    }
}

