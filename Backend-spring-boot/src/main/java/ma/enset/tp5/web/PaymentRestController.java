package ma.enset.tp5.web;

import lombok.SneakyThrows;
import ma.enset.tp5.dtos.NewPaymentDTO;
import ma.enset.tp5.entities.Payment;
import ma.enset.tp5.entities.PaymentStatus;
import ma.enset.tp5.entities.PaymentType;
import ma.enset.tp5.entities.Student;
import ma.enset.tp5.repository.PaymentRepository;
import ma.enset.tp5.repository.StudentRepository;
import ma.enset.tp5.services.PaymentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
public class PaymentRestController {
    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;

    private PaymentService paymentService;

    public PaymentRestController(StudentRepository studentRepository, PaymentRepository paymentRepository ,PaymentService paymentsService) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
        this.paymentService =  paymentsService;
    }

    @GetMapping(path = "/payments")
    public List<Payment> allPayments() {
        return paymentRepository.findAll();
    }

    @GetMapping(path = "/students/{code}/payments")
    public List<Payment> paymentsByStudent(@PathVariable String code) {
        return paymentRepository.findByStudentCode(code);
    }

    @GetMapping(path = "/payments/byType")
    public List<Payment> paymentsByType(@RequestParam PaymentType type) {
        return paymentRepository.findByType(type);
    }

    @GetMapping(path = "/payments/byStatus")
    public List<Payment> paymentsByStatus(@RequestParam PaymentStatus status) {
        return paymentRepository.findByStatus(status);
    }

    @GetMapping(path = "/payments/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return paymentRepository.findById(id).get();
    }

    @GetMapping(path = "/students")
    public List<Student> allStudents() {
        return studentRepository.findAll();
    }

    @GetMapping(path = "/students/{code}")
    public Student getStudentByCode(@PathVariable String code) {
        return studentRepository.findByCode(code);
    }

    @GetMapping(path = "/studentsByProgramId")
    public List<Student> getStudentsByProgramId(@RequestParam String programId) {
        return studentRepository.findByProgramId(programId);
    }

    @PutMapping(path = "/payments/{id}")
    public Payment updatePaymentStatus(@RequestParam("file") PaymentStatus status, @PathVariable Long id) {
        return paymentService.updatePaymentStatus(status,id);
    }

    @PostMapping(path="/payments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Payment savePayment(@RequestParam("file") MultipartFile file , NewPaymentDTO newPaymentDTO) throws IOException {
        return paymentService.savePayment(file,newPaymentDTO);

    }

    @GetMapping(path = "/paymentFile/{paymentId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPaymentFile(@PathVariable Long paymentId) throws IOException {
        return paymentService.getPaymentFile(paymentId);

    }

}

