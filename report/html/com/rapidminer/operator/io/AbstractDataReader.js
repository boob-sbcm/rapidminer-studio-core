var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":1910,"id":221312,"methods":[{"el":153,"sc":2,"sl":144},{"el":223,"sc":2,"sl":220},{"el":229,"sc":2,"sl":225},{"el":234,"sc":2,"sl":231},{"el":245,"sc":2,"sl":243},{"el":249,"sc":2,"sl":247},{"el":253,"sc":2,"sl":251},{"el":268,"sc":2,"sl":260},{"el":280,"sc":2,"sl":275},{"el":295,"sc":2,"sl":290},{"el":306,"sc":2,"sl":304},{"el":317,"sc":2,"sl":315},{"el":322,"sc":2,"sl":319},{"el":326,"sc":2,"sl":324},{"el":337,"sc":2,"sl":335},{"el":344,"sc":2,"sl":342},{"el":352,"sc":2,"sl":346},{"el":378,"sc":2,"sl":354},{"el":387,"sc":2,"sl":385},{"el":398,"sc":2,"sl":396},{"el":451,"sc":2,"sl":405},{"el":549,"sc":2,"sl":453},{"el":576,"sc":2,"sl":554},{"el":606,"sc":2,"sl":583},{"el":618,"sc":2,"sl":612},{"el":626,"sc":2,"sl":623},{"el":636,"sc":2,"sl":628},{"el":648,"sc":2,"sl":638},{"el":655,"sc":2,"sl":653},{"el":659,"sc":2,"sl":657},{"el":664,"sc":2,"sl":661},{"el":673,"sc":2,"sl":666},{"el":689,"sc":2,"sl":675},{"el":832,"sc":2,"sl":698},{"el":841,"sc":2,"sl":834},{"el":846,"sc":2,"sl":843},{"el":855,"sc":2,"sl":848},{"el":863,"sc":2,"sl":857},{"el":867,"sc":2,"sl":865},{"el":871,"sc":2,"sl":869},{"el":876,"sc":2,"sl":873},{"el":894,"sc":2,"sl":884},{"el":929,"sc":2,"sl":907},{"el":1131,"sc":2,"sl":939},{"el":1136,"sc":2,"sl":1133},{"el":1241,"sc":2,"sl":1149},{"el":1282,"sc":2,"sl":1248},{"el":1293,"sc":2,"sl":1290},{"el":1302,"sc":2,"sl":1300}],"name":"AbstractDataReader","sl":75},{"el":1329,"id":222056,"methods":[{"el":1318,"sc":3,"sl":1316},{"el":1328,"sc":3,"sl":1320}],"name":"AbstractDataReader.CacheResetParameterObserver","sl":1311},{"el":1381,"id":222065,"methods":[{"el":1347,"sc":3,"sl":1342},{"el":1356,"sc":3,"sl":1352},{"el":1367,"sc":3,"sl":1365},{"el":1376,"sc":3,"sl":1374},{"el":1380,"sc":3,"sl":1378}],"name":"AbstractDataReader.UnexpectedRowLenghtException","sl":1331},{"el":1397,"id":222079,"methods":[{"el":1396,"sc":3,"sl":1392}],"name":"AbstractDataReader.TooShortRowLengthException","sl":1383},{"el":1413,"id":222081,"methods":[{"el":1412,"sc":3,"sl":1408}],"name":"AbstractDataReader.TooLongRowLengthException","sl":1399},{"el":1481,"id":222083,"methods":[{"el":1429,"sc":3,"sl":1423},{"el":1444,"sc":3,"sl":1439},{"el":1454,"sc":3,"sl":1452},{"el":1464,"sc":3,"sl":1462},{"el":1473,"sc":3,"sl":1471},{"el":1480,"sc":3,"sl":1478}],"name":"AbstractDataReader.UnexpectedValueTypeException","sl":1415},{"el":1844,"id":222099,"methods":[{"el":1512,"sc":3,"sl":1509},{"el":1594,"sc":3,"sl":1592},{"el":1601,"sc":3,"sl":1599},{"el":1615,"sc":3,"sl":1609},{"el":1640,"sc":3,"sl":1622},{"el":1648,"sc":3,"sl":1646},{"el":1657,"sc":3,"sl":1655},{"el":1668,"sc":3,"sl":1666},{"el":1678,"sc":3,"sl":1676},{"el":1689,"sc":3,"sl":1686},{"el":1696,"sc":3,"sl":1694},{"el":1705,"sc":3,"sl":1703},{"el":1714,"sc":3,"sl":1712},{"el":1746,"sc":3,"sl":1716},{"el":1763,"sc":3,"sl":1748},{"el":1832,"sc":3,"sl":1785},{"el":1843,"sc":3,"sl":1839}],"name":"AbstractDataReader.AttributeColumn","sl":1499},{"el":1908,"id":222219,"methods":[],"name":"AbstractDataReader.DataSet","sl":1846}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], []]