package com.prelude.social_app;

import com.prelude.social_app.model.Roles;
import com.prelude.social_app.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SocialAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialAppApplication.class, args);
	}
	@Bean
	public CommandLineRunner loadData(RoleRepository roleRepository) {
		return args -> {
			// Kiểm tra xem vai trò USER đã tồn tại chưa
			if (roleRepository.findByAuthority("USER").isEmpty()) {
				// Tạo một đối tượng Roles mới
				Roles userRole = new Roles();
				userRole.setAuthority("USER");

				// Lưu vai trò vào cơ sở dữ liệu
				roleRepository.save(userRole);
				System.out.println("Role 'USER' has been added.");
			} else {
				System.out.println("Role 'USER' already exists.");
			}
		};
	}
}

