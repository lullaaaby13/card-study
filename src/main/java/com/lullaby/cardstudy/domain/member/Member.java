package com.lullaby.cardstudy.domain.member;


import com.lullaby.cardstudy.common.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String account;
    private String password;
    private String nickname;
    private String email;


    public Member(String account, String password, String nickname, String email) {
        setAccount(account);
        setPassword(password);
        setNickname(nickname);
        setEmail(email);
    }

    public void setAccount(String account) {
        if (account == null || account.isBlank()) {
            throw new IllegalArgumentException("아이디를 입력해 주세요.");
        }
        this.account = account;
    }

    public void setPassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호를 입력해 주세요.");
        }
        this.password = password;
    }

    public void setNickname(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            throw new IllegalArgumentException("닉네임을 입력해 주세요.");
        }
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("이메일을 입력해 주세요.");
        }
        this.email = email;
    }

}
