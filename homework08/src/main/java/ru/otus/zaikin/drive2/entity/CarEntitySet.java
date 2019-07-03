package ru.otus.zaikin.drive2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "STTM_CAR")
public class CarEntitySet extends DataSet {
    @Column
    String href;

    @Column
    Date issueDate;

    @Column
    Double price;

    @Column
    String currency;

    @Column
    String carBrend;

    @Column
    String model;

    @Column
    Double engineVolume;

    @Column
    String engineType;

    @Column
    int enginePower;
}
