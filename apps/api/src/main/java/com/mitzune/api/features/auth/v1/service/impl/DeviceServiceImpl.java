package com.mitzune.api.features.auth.v1.service.impl;

import com.mitzune.api.features.auth.v1.service.DeviceService;
import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl implements DeviceService {

  private final UserAgentAnalyzer userAgentAnalyzer;

  private DeviceServiceImpl() {
    this.userAgentAnalyzer = UserAgentAnalyzer.newBuilder()
      .withCache(100)
      .build();
  }

  @Override
  public String parseDevice(String userAgentString) {
    UserAgent userAgent = userAgentAnalyzer.parse(userAgentString);

    return String.format(
      "%s on %s",
      userAgent.getValue("AgentNameVersion"),
      userAgent.getValue("OperatingSystemNameVersion")
    );
  }
}
