package ru.otus.zaikin.yandex.market.indexpage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NavMenuItem {
    private String caption;
    private String href;
}
