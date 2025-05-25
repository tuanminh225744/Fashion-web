package ktpm.projectsoftware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ktpm.projectsoftware.service.DichVuDatHang;
import ktpm.projectsoftware.service.DichVuDonHang;

@SpringBootApplication
public class ProjectsoftwareApplication  {
	
	public static void main(String[] args) {
		SpringApplication.run(ProjectsoftwareApplication.class, args);
	}


}
