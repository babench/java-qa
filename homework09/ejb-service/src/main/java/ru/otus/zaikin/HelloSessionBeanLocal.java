package ru.otus.zaikin;

import javax.ejb.Local;
import java.util.Date;

@Local
public interface HelloSessionBeanLocal {
    Date getTime();
}
