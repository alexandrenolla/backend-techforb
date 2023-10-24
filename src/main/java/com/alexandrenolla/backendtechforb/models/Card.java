package com.alexandrenolla.backendtechforb.models;

import java.math.BigDecimal;

import com.alexandrenolla.backendtechforb.models.User.CreateUser;
import com.alexandrenolla.backendtechforb.models.User.UpdateUser;

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
@Table(name = Card.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Card {

    public interface CreateCard {
    }

    public static final String TABLE_NAME = "card";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Column(name = "name", length = 100, nullable = false, unique = true)
    @NotNull(groups = CreateCard.class)
    @NotEmpty(groups = CreateCard.class)
    @Size(groups = CreateCard.class, min = 2, max = 100)
    private String name;

    @Column(name = "number", length = 255, nullable = false)
    @NotNull(groups = CreateCard.class)
    private BigDecimal number;

    @Column(name = "securityCode", length = 255, nullable = false)
    @NotNull(groups = CreateCard.class)
    private BigDecimal securityCode;

    @Column(name = "expirationDate", length = 255, nullable = false)
    @NotNull(groups = CreateCard.class)
    private String expirationDate;
}
