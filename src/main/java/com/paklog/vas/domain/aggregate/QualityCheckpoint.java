package com.paklog.vas.domain.aggregate;

import com.paklog.vas.domain.valueobject.QualityGrade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualityCheckpoint {

    private String checkpointId;
    private String stepId;
    private String inspectorId;

    @Builder.Default
    private List<String> checklistItems = new ArrayList<>();

    @Builder.Default
    private List<String> passedItems = new ArrayList<>();

    @Builder.Default
    private List<String> failedItems = new ArrayList<>();

    private QualityGrade grade;
    private boolean passed;

    private String photoUrl;
    private String notes;

    private Instant inspectedAt;

    public void perform() {
        this.inspectedAt = Instant.now();
    }

    public void evaluateResults() {
        int totalItems = checklistItems.size();
        int passedCount = passedItems.size();

        if (totalItems == 0) {
            this.passed = true;
            this.grade = QualityGrade.A_EXCELLENT;
            return;
        }

        double passRate = (double) passedCount / totalItems;

        if (passRate >= 0.95) {
            this.grade = QualityGrade.A_EXCELLENT;
            this.passed = true;
        } else if (passRate >= 0.85) {
            this.grade = QualityGrade.B_GOOD;
            this.passed = true;
        } else if (passRate >= 0.75) {
            this.grade = QualityGrade.C_ACCEPTABLE;
            this.passed = true;
        } else {
            this.grade = QualityGrade.D_FAILED;
            this.passed = false;
        }
    }

    public void addPassedItem(String item) {
        if (!passedItems.contains(item)) {
            passedItems.add(item);
        }
    }

    public void addFailedItem(String item) {
        if (!failedItems.contains(item)) {
            failedItems.add(item);
        }
    }
}
