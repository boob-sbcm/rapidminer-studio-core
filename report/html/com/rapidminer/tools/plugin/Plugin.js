var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":1675,"id":4225,"methods":[{"el":142,"sc":5,"sl":139},{"el":147,"sc":2,"sl":135},{"el":308,"sc":2,"sl":265},{"el":331,"sc":2,"sl":320},{"el":346,"sc":2,"sl":337},{"el":361,"sc":2,"sl":354},{"el":366,"sc":2,"sl":364},{"el":371,"sc":2,"sl":369},{"el":376,"sc":2,"sl":374},{"el":383,"sc":2,"sl":381},{"el":387,"sc":2,"sl":385},{"el":391,"sc":2,"sl":389},{"el":395,"sc":2,"sl":393},{"el":399,"sc":2,"sl":397},{"el":403,"sc":2,"sl":401},{"el":407,"sc":2,"sl":405},{"el":411,"sc":2,"sl":409},{"el":418,"sc":2,"sl":416},{"el":425,"sc":2,"sl":423},{"el":430,"sc":2,"sl":428},{"el":439,"sc":2,"sl":437},{"el":454,"sc":5,"sl":451},{"el":462,"sc":2,"sl":445},{"el":485,"sc":2,"sl":465},{"el":529,"sc":2,"sl":488},{"el":544,"sc":2,"sl":531},{"el":562,"sc":2,"sl":546},{"el":572,"sc":2,"sl":564},{"el":582,"sc":2,"sl":577},{"el":587,"sc":2,"sl":585},{"el":638,"sc":2,"sl":589},{"el":699,"sc":2,"sl":645},{"el":725,"sc":2,"sl":702},{"el":748,"sc":5,"sl":745},{"el":752,"sc":2,"sl":731},{"el":799,"sc":2,"sl":758},{"el":833,"sc":2,"sl":814},{"el":838,"sc":2,"sl":835},{"el":859,"sc":2,"sl":849},{"el":879,"sc":2,"sl":871},{"el":912,"sc":2,"sl":884},{"el":928,"sc":2,"sl":918},{"el":970,"sc":2,"sl":935},{"el":1025,"sc":2,"sl":976},{"el":1037,"sc":2,"sl":1031},{"el":1042,"sc":2,"sl":1040},{"el":1047,"sc":2,"sl":1045},{"el":1056,"sc":2,"sl":1054},{"el":1061,"sc":2,"sl":1059},{"el":1073,"sc":2,"sl":1064},{"el":1081,"sc":2,"sl":1079},{"el":1090,"sc":2,"sl":1088},{"el":1094,"sc":2,"sl":1092},{"el":1098,"sc":2,"sl":1096},{"el":1102,"sc":2,"sl":1100},{"el":1125,"sc":2,"sl":1104},{"el":1160,"sc":2,"sl":1131},{"el":1167,"sc":2,"sl":1162},{"el":1171,"sc":2,"sl":1169},{"el":1186,"sc":2,"sl":1173},{"el":1216,"sc":2,"sl":1193},{"el":1324,"sc":4,"sl":1321},{"el":1349,"sc":2,"sl":1224},{"el":1371,"sc":2,"sl":1355},{"el":1393,"sc":2,"sl":1377},{"el":1405,"sc":2,"sl":1396},{"el":1445,"sc":2,"sl":1413},{"el":1457,"sc":2,"sl":1448},{"el":1484,"sc":2,"sl":1463},{"el":1565,"sc":2,"sl":1487},{"el":1570,"sc":2,"sl":1568},{"el":1575,"sc":2,"sl":1573},{"el":1586,"sc":2,"sl":1584},{"el":1594,"sc":2,"sl":1592},{"el":1598,"sc":2,"sl":1596},{"el":1602,"sc":2,"sl":1600},{"el":1606,"sc":2,"sl":1604},{"el":1613,"sc":2,"sl":1611},{"el":1624,"sc":2,"sl":1618},{"el":1643,"sc":2,"sl":1629},{"el":1654,"sc":2,"sl":1648},{"el":1674,"sc":2,"sl":1661}],"name":"Plugin","sl":119}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], []]