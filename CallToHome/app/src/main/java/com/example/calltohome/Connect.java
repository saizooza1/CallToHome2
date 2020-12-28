package com.example.calltohome;



import com.adedom.library.Dru;

import java.sql.Connection;

public class Connect {
    public static Connection connection() {
        return Dru.connection("192.168.1.13", "drusp", "drusp", "callm");

    }
}

