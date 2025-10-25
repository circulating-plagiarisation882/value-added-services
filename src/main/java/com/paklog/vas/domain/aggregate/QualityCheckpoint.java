package com.paklog.vas.domain.aggregate;

import com.paklog.vas.domain.valueobject.QualityGrade;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class QualityCheckpoint {

 private String checkpointId;
 private String stepId;
 private String inspectorId;

 private List<String> checklistItems = new ArrayList<>();

 private List<String> passedItems = new ArrayList<>();

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

 // Getters
 public String getCheckpointId() { return checkpointId; }
 public String getStepId() { return stepId; }
 public String getInspectorId() { return inspectorId; }
 public QualityGrade getGrade() { return grade; }
 public boolean isPassed() { return passed; }
 public String getPhotoUrl() { return photoUrl; }
 public String getNotes() { return notes; }
 public Instant getInspectedAt() { return inspectedAt; }

 // Setters
 public void setCheckpointId(String checkpointId) { this.checkpointId = checkpointId; }
 public void setStepId(String stepId) { this.stepId = stepId; }
 public void setInspectorId(String inspectorId) { this.inspectorId = inspectorId; }
 public void setGrade(QualityGrade grade) { this.grade = grade; }
 public void setPassed(boolean passed) { this.passed = passed; }
 public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
 public void setNotes(String notes) { this.notes = notes; }
 public void setInspectedAt(Instant inspectedAt) { this.inspectedAt = inspectedAt; }

 // Builder
 public static Builder builder() { return new Builder(); }

 public static class Builder {
 private String checkpointId;
 private String stepId;
 private String inspectorId;
 private QualityGrade grade;
 private boolean passed;
 private String photoUrl;
 private String notes;
 private Instant inspectedAt;

 public Builder checkpointId(String checkpointId) { this.checkpointId = checkpointId; return this; }
 public Builder stepId(String stepId) { this.stepId = stepId; return this; }
 public Builder inspectorId(String inspectorId) { this.inspectorId = inspectorId; return this; }
 public Builder grade(QualityGrade grade) { this.grade = grade; return this; }
 public Builder passed(boolean passed) { this.passed = passed; return this; }
 public Builder photoUrl(String photoUrl) { this.photoUrl = photoUrl; return this; }
 public Builder notes(String notes) { this.notes = notes; return this; }
 public Builder inspectedAt(Instant inspectedAt) { this.inspectedAt = inspectedAt; return this; }

 public QualityCheckpoint build() {
 QualityCheckpoint obj = new QualityCheckpoint();
 obj.checkpointId = this.checkpointId;
 obj.stepId = this.stepId;
 obj.inspectorId = this.inspectorId;
 obj.grade = this.grade;
 obj.passed = this.passed;
 obj.photoUrl = this.photoUrl;
 obj.notes = this.notes;
 obj.inspectedAt = this.inspectedAt;
 return obj;
 

}
}
}
