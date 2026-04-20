package com.cinema.config;

import com.cinema.model.*;
import com.cinema.pattern.factory.UserFactory;
import com.cinema.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    private final MovieRepository movieRepository;
    private final TheatreRepository theatreRepository;
    private final ScreenRepository screenRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;
    private final UserFactory userFactory;

    public DataLoader(MovieRepository movieRepository, TheatreRepository theatreRepository,
                      ScreenRepository screenRepository, ShowRepository showRepository,
                      SeatRepository seatRepository, UserRepository userRepository, UserFactory userFactory) {
        this.movieRepository = movieRepository;
        this.theatreRepository = theatreRepository;
        this.screenRepository = screenRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;
        this.userFactory = userFactory;
    }

    @Override
    public void run(String... args) {
        if (movieRepository.count() > 0) return;

        Movie m1 = movieRepository.save(new Movie("Inception", "Sci-Fi", 148));
        Movie m2 = movieRepository.save(new Movie("The Dark Knight", "Action", 152));
        Movie m3 = movieRepository.save(new Movie("Dune", "Sci-Fi", 155));

        Theatre t1 = theatreRepository.save(new Theatre("PVR Cinemas", "Koramangala"));
        Theatre t2 = theatreRepository.save(new Theatre("INOX", "Indiranagar"));

        Screen s1 = screenRepository.save(new Screen(1, 20, t1));
        Screen s2 = screenRepository.save(new Screen(2, 25, t1));
        Screen s3 = screenRepository.save(new Screen(1, 30, t2));

        LocalDateTime base = LocalDate.now().atTime(10, 0);
        Show sh1 = showRepository.save(new Show(base.plusDays(1), 250, m1, s1));
        Show sh2 = showRepository.save(new Show(base.plusDays(1).plusHours(3), 300, m2, s1));
        Show sh3 = showRepository.save(new Show(base.plusDays(2), 200, m3, s2));

        for (Show sh : List.of(sh1, sh2, sh3)) {
            for (int i = 1; i <= sh.getScreen().getTotalSeats(); i++) {
                String type = i <= 5 ? "PREMIUM" : (i <= 15 ? "NORMAL" : "RECLINER");
                seatRepository.save(new Seat("S" + i, type, sh));
            }
        }

        if (!userRepository.existsByEmail("admin@cinema.com")) {
            User admin = userFactory.createAdmin("Admin", "admin@cinema.com", "9999999999", "admin123");
            userRepository.save(admin);
        }
        if (!userRepository.existsByEmail("user@test.com")) {
            User user = userFactory.createUser("Test User", "user@test.com", "8888888888", "user123");
            userRepository.save(user);
        }
    }
}
