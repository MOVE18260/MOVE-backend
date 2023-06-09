package dev.ehyeon.move.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "member")
    private final List<Record> records = new ArrayList<>();

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String sex;

    @Column(nullable = false)
    private String province;

    private Integer point;

    public Member(String email, String password, String nickname, LocalDate birthDate,
            String sex, String province) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.birthDate = birthDate;
        this.sex = sex;
        this.province = province;
        this.point = 0;
    }

    public void addPoint(int point) {
        this.point += point;
    }
}
