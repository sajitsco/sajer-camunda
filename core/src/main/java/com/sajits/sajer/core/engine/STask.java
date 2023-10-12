package com.sajits.sajer.core.engine;

import java.util.Map;

import com.sajits.sajer.core.entities.DataStructure;
import com.sajits.sajer.core.entities.Section;

public class STask {
        private String id;
        private String name;
        private DataStructure dataStructure;
        private TaskLifecycleStates state;
        private TaskLifecycleOperations operations[];
        private Section info;
        private Map<String, Object> variables;

        public Map<String, Object> getVariables() {
            return variables;
        }
        public void setVariables(Map<String, Object> variables) {
            this.variables = variables;
        }
        public Section getInfo() {
            return info;
        }
        public void setInfo(Section info) {
            this.info = info;
        }
        public TaskLifecycleStates getState() {
            return state;
        }
        public void setState(TaskLifecycleStates state) {
            this.state = state;
        }
        public TaskLifecycleOperations[] getOperations() {
            return operations;
        }
        public void setOperations(TaskLifecycleOperations[] operations) {
            this.operations = operations;
        }
        public DataStructure getDataStructure() {
            return dataStructure;
        }
        public void setDataStructure(DataStructure dataStructure) {
            this.dataStructure = dataStructure;
        }

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
}
