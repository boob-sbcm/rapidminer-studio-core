var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":302,"id":92127,"methods":[{"el":87,"sc":2,"sl":76},{"el":102,"sc":2,"sl":98},{"el":135,"sc":2,"sl":107},{"el":147,"sc":2,"sl":143},{"el":154,"sc":2,"sl":149},{"el":172,"sc":2,"sl":167},{"el":182,"sc":2,"sl":180},{"el":192,"sc":2,"sl":190}],"name":"ProxySettings","sl":39},{"el":266,"id":92179,"methods":[{"el":234,"sc":3,"sl":215},{"el":252,"sc":3,"sl":247},{"el":264,"sc":3,"sl":260}],"name":"ProxySettings.ProxyIntegrator","sl":203},{"el":300,"id":92207,"methods":[{"el":282,"sc":3,"sl":280},{"el":291,"sc":3,"sl":289},{"el":298,"sc":3,"sl":296}],"name":"ProxySettings.SystemSettings","sl":271}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], []]