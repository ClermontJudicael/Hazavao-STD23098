package com.hazavao.demo.datastructure.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatResponse {
    private List<Choice> choices;

    @AllArgsConstructor
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Choice {
        private int index;
        private Message message;
    }
}
