package com.mybank.accounts;

import java.util.List;
import java.util.Map;

public record Properties (String msg, String buildVersion, Map<String, String> mailDetails, List<String> activeBranches) {

}
