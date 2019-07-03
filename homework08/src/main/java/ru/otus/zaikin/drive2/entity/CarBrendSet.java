package ru.otus.zaikin.drive2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "STTM_CAR_BREND")
public class CarBrendSet extends DataSet {
    @Column
    String name;
}
