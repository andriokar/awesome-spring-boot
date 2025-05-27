package com.andriokar.maven.config;

import com.andriokar.maven.services.BluePrinter;
import com.andriokar.maven.services.ColourPrinter;
import com.andriokar.maven.services.GreenPrinter;
import com.andriokar.maven.services.RedPrinter;
import com.andriokar.maven.services.impl.ColourPrinterImpl;
import com.andriokar.maven.services.impl.SpanishBluePrinter;
import com.andriokar.maven.services.impl.SpanishGreenPrinter;
import com.andriokar.maven.services.impl.SpanishRedPrinter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrinterConfig {

    @Bean
    public BluePrinter bluePrinter() {
        return new SpanishBluePrinter();
    }

    @Bean
    public RedPrinter redPrinter() {
        return new SpanishRedPrinter();
    }

    @Bean
    public GreenPrinter greenPrinter() {
        return new SpanishGreenPrinter();
    }

    @Bean
    public ColourPrinter colourPrinter(
            BluePrinter bluePrinter, RedPrinter redPrinter, GreenPrinter greenPrinter) {
        return new ColourPrinterImpl(redPrinter, bluePrinter, greenPrinter);
    }
}
