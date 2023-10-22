package com.alexandrenolla.backendtechforb.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import com.alexandrenolla.backendtechforb.models.User.CreateUser;
import com.alexandrenolla.backendtechforb.models.User.UpdateUser;
import com.alexandrenolla.backendtechforb.models.Enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = Task.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Task {
    public static final String TABLE_NAME = "task";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Column(name = "description", length = 255, nullable = false)
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 255)
    private String description;

    public void setDescription(String description) {

        if (!Arrays.asList(TransactionType.values()).stream()
                .anyMatch(type -> type.name().equalsIgnoreCase(description))) {
            throw new IllegalArgumentException("Invalid transaction type. Possible types: DEPOSIT, TRANSFER or WITHDRAW");
        }
        this.description = description;
    }


    @Column(name = "value", length = 255, nullable = false)
    @NotNull
    @DecimalMin(groups = {CreateUser.class, UpdateUser.class}, value = "0.1")
    private BigDecimal value;

}
