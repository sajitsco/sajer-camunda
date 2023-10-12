package com.sajits.sajer.core.auth.setting;

import java.util.HashMap;
import java.util.Map;

public class Setting {
    private Map<String,Section> sections;

    public Map<String,Section> getSections() {
        return sections;
    }

    public void setSections(HashMap<String,Section> sections) {
        this.sections = sections;
    }
}
