package com.example.calltohome;


import com.adedom.library.Dru;

import java.sql.Connection;

public class Connect {
    public static Connection connection() {
        return Dru.connection("192.168.0.200", "drusp", "drusp", "callm");

    }
}

