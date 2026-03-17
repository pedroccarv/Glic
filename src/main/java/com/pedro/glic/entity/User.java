package com.pedro.glic.entity;

import com.pedro.glic.dto.UserRequestDTO;
import com.pedro.glic.enums.DiabetesType;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tb_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private LocalDate birthday;

    private LocalDate diagnostic;

    private String phone;

    private Integer carbCounting;

    @Enumerated(EnumType.STRING)
    private DiabetesType diabetesType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Insulin> insulins = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ApplicationLog> applicationLogs = new ArrayList<>();

    public User() {}

    public User(String name, String email, String password, LocalDate birthday, LocalDate diagnostic, String phone, Integer carbCounting, DiabetesType diabetesType) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.diagnostic = diagnostic;
        this.phone = phone;
        this.carbCounting = carbCounting;
        this.diabetesType = diabetesType;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Integer getCarbCounting() {
        return carbCounting;
    }

    public void setCarbCounting(Integer carbCounting) {
        this.carbCounting = carbCounting;
    }

    public DiabetesType getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(DiabetesType diabetesType) {
        this.diabetesType = diabetesType;
    }

    public LocalDate getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(LocalDate diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Insulin> getInsulins(){
        return insulins;
    }

    public List<ApplicationLog> getApplicationLogs(){
        return applicationLogs;
    }

    public void addInsulin(Insulin insulin){
        this.insulins.add(insulin);
        insulin.setUser(this);

    }

    public void addApplicationLog(ApplicationLog log){
        this.applicationLogs.add(log);
        log.setUser(this);
    }

    @Override
    public String getUsername() {
        return email; // Spring Security usa email como "username"
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }

    public void updateFrom(UserRequestDTO dto) {
        if (dto.name() != null) this.name = dto.name();
        if (dto.email() != null) this.email = dto.email();
        if (dto.phone() != null) this.phone = dto.phone();
        if (dto.birthday() != null) this.birthday = dto.birthday();
        if (dto.diagnostic() != null) this.diagnostic = dto.diagnostic();
        if (dto.carbCounting() != null) this.carbCounting = dto.carbCounting();
        if (dto.diabetesType() != null) this.diabetesType = dto.diabetesType();
    }

    @Transient
    public Integer getAge(){
        if (birthday == null){
            return null;
        }
        Period period = Period.between(birthday, LocalDate.now());
        return period.getYears();
    }



    @Transient
    public String getTimeSinceDiagnosis() {
        if (diagnostic == null){
            return null;
        }
        Period period = Period.between(diagnostic, LocalDate.now());

        return period.getYears() + " anos, "
                + period.getMonths() + " mes e "
                + period.getDays() + " dias ";
    }
}
