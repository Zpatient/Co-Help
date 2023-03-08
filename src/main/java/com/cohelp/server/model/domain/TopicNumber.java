package com.cohelp.server.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zgy
 * @create 2023-02-01 9:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicNumber {
    private List<Integer> activityNumber;
    private List<Integer> helpNumber;
    private List<Integer> holeNumber;
    private List<Integer> askNumber;
}
