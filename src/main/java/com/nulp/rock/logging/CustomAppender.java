package com.nulp.rock.logging;

import ch.qos.logback.core.AppenderBase;
import com.nulp.rock.common.Logger;

public class CustomAppender extends AppenderBase {

    @Override
    protected void append(Object arg0) {
        try {
            Logger.append(arg0.toString());
        } catch (Exception e) {
        }
    }

}
