package ma.enset.tp5.services;

import jakarta.transaction.Transactional;
import ma.enset.tp5.dtos.NewPaymentDTO;
import ma.enset.tp5.entities.Payment;
import ma.enset.tp5.entities.PaymentStatus;
import ma.enset.tp5.entities.PaymentType;
import ma.enset.tp5.entities.Student;
import ma.enset.tp5.repository.PaymentRepository;
import ma.enset.tp5.repository.StudentRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {

    private PaymentRepository paymentRepository;
    private StudentRepository studentRepository;

    public PaymentService(PaymentRepository paymentRepository, StudentRepository studentRepository) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
    }

    public Payment savePayment(MultipartFile file, NewPaymentDTO newPaymentDTO) throws IOException {
        Path folderPath = Paths.get(System.getProperty("user.home"),"enset-students","payments");
        if(!Files.exists(folderPath)){
            Files.createDirectories(folderPath);
        }
        String fileName = UUID.randomUUID().toString();
        Path filePath = Paths.get(System.getProperty("user.home"),"enset-students","payments",fileName+".pdf");
        Files.copy(file.getInputStream(), filePath);
        Student student = studentRepository.findByCode(newPaymentDTO.getStudentCode());
        Payment payment=Payment.builder()
                .type(newPaymentDTO.getType())
                .status(PaymentStatus.CREATED)
                .date(newPaymentDTO.getDate())
                .student(student)
                .amount(newPaymentDTO.getAmount())
                .file(filePath.toUri().toString())
                .build();
        return paymentRepository.save(payment);

    }

    public byte[] getPaymentFile(Long id) throws IOException {
        Payment payment = paymentRepository.findById(id).get();
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));

    }

    public Payment updatePaymentStatus(PaymentStatus status, Long paymentId){
        Payment payment = paymentRepository.findById(paymentId).get();
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }
}