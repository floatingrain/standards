public final class SensitiveLogDataConverter extends MessageConverter {
    //银行卡正则
    private final static Pattern BANK_CARD_PATTERN = Pattern.compile("(\\D)([3-6]\\d{3})(\\d{8,12})(\\d{4})(\\D)");
    //手机号正则
    private final static Pattern PHONE_PATTERN = Pattern.compile("(?<!\\d)(1\\d{10})(?!\\d)");
    //身份证号正则
    private final static Pattern ID_CARD_PATTERN = Pattern.compile("(?<!\\d)(\\d{6})([19,20]\\d{7})(\\d{3}[0-9Xx])" +
            "(?!\\d)");

    @Override
    public String convert(ILoggingEvent event) {
        String log = event.getFormattedMessage();
        return logDesensitization(new Pattern[]{PHONE_PATTERN}, log);
    }

    public String logDesensitization(Pattern[] patterns, final String log) {
        StringBuilder logBuilder = new StringBuilder(log);
        //不同的匹配规则依次处理
        for (Pattern pattern : patterns) {
            //每次find都从上一次找到的匹配项的下一个索引开始
            while (true) {
                //Matcher内有各种索引缓存，所以每次匹配都更新Matcher
                Matcher matcher = pattern.matcher(logBuilder);
                if (matcher.find()) {
                    //StringBuilder delete之后matcher就group不到了，因此缓存group
                    String temp = matcher.group();
                    logBuilder.delete(matcher.start(), matcher.end());
                    logBuilder.insert(matcher.start(), DigestUtils.sha256Hex(temp));
                } else
                    break;
            }
        }
        return logBuilder.toString();
    }
}