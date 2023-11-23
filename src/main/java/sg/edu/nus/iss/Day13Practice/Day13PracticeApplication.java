package sg.edu.nus.iss.Day13Practice;

import java.io.File;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Day13PracticeApplication implements ApplicationRunner{

	public static void main(String[] args) {
		SpringApplication.run(Day13PracticeApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		if (args.containsOption("dataDir")) {
			final String dataDir = args.getOptionValues("dataDir").get(0); // To create

			File fileDir = new File(dataDir); // Check if the directory exists

			if (!fileDir.exists()) {
				fileDir.mkdir(); // Creates a directory.
				System.out.println("***" + fileDir.getAbsolutePath());
				System.out.println("***" + fileDir.getPath());
				System.out.println("***" + fileDir.getParent());
			} else {
				System.out.println(fileDir.getAbsolutePath()); // Not sure what this does.
			}

		}


	}

}
