package com.service.notification.core.exceptions;

import java.util.Date;

public record ErrorInfo(String url, String ex, String response, Date date) {

}
