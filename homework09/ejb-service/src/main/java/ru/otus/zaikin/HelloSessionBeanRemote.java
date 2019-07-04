package ru.otus.zaikin;

import javax.ejb.Remote;
import java.util.Date;

@Remote
public interface HelloSessionBeanRemote {
    Date getTime();
}
