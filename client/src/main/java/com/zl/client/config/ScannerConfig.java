package com.zl.client.config;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Attributes;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

@Component
public class ScannerConfig {

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }

    @Bean
    public LineReader lineReader() throws IOException {
        Attributes attributes = new Attributes();
        Terminal terminal = TerminalBuilder.builder()
                .system(true)
                .size(new Size(10, 1))
                .attributes(attributes)
                .build();
        LineReader lineReader = LineReaderBuilder.builder().terminal(terminal).build();
        return lineReader;
    }


}
