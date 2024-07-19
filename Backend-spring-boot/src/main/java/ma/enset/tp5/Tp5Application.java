package ma.enset.tp5;

import ma.enset.tp5.entities.Payment;
import ma.enset.tp5.entities.PaymentStatus;
import ma.enset.tp5.entities.PaymentType;
import ma.enset.tp5.entities.Student;
import ma.enset.tp5.repository.PaymentRepository;
import ma.enset.tp5.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class Tp5Application {

	public static void main(String[] args) {
		SpringApplication.run(Tp5Application.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository, PaymentRepository paymentRepository){
		return args -> {
			studentRepository.save(Student.builder()
					.id(UUID.randomUUID().toString())
							.firstName("Achraf").code("202401").programId("SDIA")
					.build());

			studentRepository.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.firstName("Anas").code("202402").programId("RI")
					.build());

			studentRepository.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.firstName("Reda").code("202403").programId("CS")
					.build());

			studentRepository.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.firstName("Aymane").code("202404").programId("BDCC")
					.build());


			PaymentType[] paymentTypes = PaymentType.values();
			Random random = new Random();
			studentRepository.findAll().forEach(st -> {
				for (int i=0; i<10; i++){
					int index = random.nextInt(paymentTypes.length);
					Payment payment = Payment.builder()
							.amount(1000+Math.random()+20000)
							.type(paymentTypes[index])
							.status(PaymentStatus.CREATED)
							.date(LocalDate.now())
							.student(st)
							.build();
					paymentRepository.save(payment);
				}
			});
		};
	}
}
