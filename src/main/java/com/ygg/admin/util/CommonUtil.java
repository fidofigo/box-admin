package com.ygg.admin.util;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.joda.time.DateTime;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;
import com.google.gson.Gson;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.py.Pinyin;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

public class CommonUtil
{
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    
    /**
     * 算法常量： MD5
     */
    private static final String ALGORITHM_MD5 = "MD5";
    
    /**
     * 算法常量： SHA1
     */
    private static final String ALGORITHM_SHA1 = "SHA-1";
    
    /**
     * 算法常量：SHA1withRSA
     */
    private static final String BC_PROV_ALGORITHM_SHA1RSA = "SHA1withRSA";
    
    public static Logger log = Logger.getLogger(CommonUtil.class);
    
    private static char[] letter = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    
    private static Random random = new Random();
    
    private static Gson gson = new Gson();
    
    private static String regEx = "[\u4e00-\u9fa5]";
    
    private static Pattern pat = Pattern.compile(regEx);
    
    /**
     * 生成唯一的外部订单号
     */
    public static long generateOutNumber()
    {
        try
        {
            Thread.sleep(1);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        String number = "70" + System.currentTimeMillis() + random.nextInt(10);
        return Long.valueOf(number);
    }
    
    /**
     * 判断订单号是手工订单还是正常订单
     *
     * @param number
     * @return 1 : 手工订单 (201505077204531)，2 : 正常订单
     */
    public static int estimateOrderNumber(String number)
    {
        // 这个时间后才有手动订单
        Date earlyDate = string2Date("2015-05-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
        String n2to4 = number.substring(2, 4); // 手动订单是年份，正常订单是月份。此方法有效期为84年， 2015 - 2099
        if (n2to4.startsWith("0"))
        {
            n2to4 = n2to4.substring(1);
        }
        if (Integer.parseInt(n2to4) >= 15)
        {
            String dateStr = number.substring(0, 8);
            Date currOrderDate = string2Date(dateStr, "yyyyMMdd");
            if (currOrderDate.before(earlyDate))
            {
                return 2;
            }
            return 1;
        }
        else
        {
            // 正常订单
            return 2;
        }
    }
    
    public static String generateCode()
    {
        String hexlong = random.nextInt(10) + "" + System.currentTimeMillis() + "" + random.nextInt(10);
        return Long.toHexString(Long.valueOf(hexlong));
    }
    
    /**
     * 检查收货地址姓名是否要求
     *
     * @param content
     * @return
     */
    public static boolean validateReceiveName(String content)
    {
        // 长度只允许2-5个字符
        if (content == null || content.length() < 2 || content.length() > 5)
        {
            return false;
        }
        // 只允许中文名字
        String patt1 = "^[\\u4e00-\\u9fa5]+$";
        if (!matchPatt(content, patt1))
        {
            return false;
        }
        // 不允许 开头第1个字是"小","阿"
        if (content.startsWith("小") || content.startsWith("阿"))
        {
            return false;
        }
        // 不允许 小姐\u5c0f\u59d0 ； 女士\u5973\u58eb ； 先生\u5148\u751f
        if (content.contains("小姐") || content.contains("女士") || content.contains("先生") || content.contains("老师") || content.contains("医生") || content.contains("公司"))
        {
            return false;
        }
        // 不允许所有中文字相同
        char[] arr = content.toCharArray();
        Set s = new HashSet();
        for (char c : arr)
        {
            s.add(c);
        }
        if (s.size() == 1)
        {
            return false;
        }
        return true;
    }
    
    public static boolean matchPatt(String content, String patt)
    {
        Pattern pattern = Pattern.compile(patt);
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }
    
    /**
     * 生成指定长度的随机数字
     *
     * @return 验证通过返回true
     */
    public static String GenerateRandomCode(int length)
    {
        String result = "";
        for (int i = 0; i < length; i++)
        {
            result += nextInt(0, 9);
        }
        return result;
    }
    
    private static int nextInt(final int min, final int max)
    {
        Random rand = new Random();
        int tmp = Math.abs(rand.nextInt());
        return tmp % (max - min + 1) + min;
    }
    
    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobileNumber(String str)
    {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
    
    /**
     * 电话号码验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isPhone(String str)
    {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
        if (str.length() > 9)
        {
            m = p1.matcher(str);
            b = m.matches();
        }
        else
        {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }
    
    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str)
    {
        if (str == null || str.equals(""))
        {
            return true;
        }
        return false;
    }
    
    /**
     * 把字符串转换成md5
     *
     * @param str
     * @return
     */
    public static String strToMD5(String str)
        throws UnsupportedEncodingException
    {
        
        try
        {
            // return DigestUtils.md5Hex(str).substring(8,24).toUpperCase();
            byte[] input;
            input = str.getBytes(CommonConstant.CHARACTER_ENCODING);
            return bytesToHex(bytesToMD5(input));
        }
        catch (UnsupportedEncodingException e)
        {
            log.error("strToMD5编码不支持!", e);
            throw e;
        }
    }
    
    /**
     * 把字节数组转成16进位制数
     *
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes)
    {
        StringBuffer md5str = new StringBuffer();
        // 把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 4; i < bytes.length - 4; i++)
        {
            digital = bytes[i];
            if (digital < 0)
            {
                digital += 256;
            }
            if (digital < 16)
            {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toUpperCase();
    }
    
    /**
     * 把字节数组转换成md5
     *
     * @param input
     * @return
     */
    public static byte[] bytesToMD5(byte[] input)
    {
        // String md5str = null;
        byte[] buff = null;
        try
        {
            // 创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算后获得字节数组
            buff = md.digest(input);
            // 把数组每一字节换成16进制连成md5字符串
            // md5str = bytesToHex(buff);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return buff;
    }
    
    /**
     * 将字符串格式装换成DATE类型
     *
     * @param date
     * @param type 被转换的字符串格式，如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date string2Date(String date, String type)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        Date time = null;
        try
        {
            time = sdf.parse(date);
        }
        catch (ParseException e)
        {
            log.error("字符串格式装换成DATE类型出错", e);
        }
        return time;
    }
    
    /**
     * 将DATE类型装换成字符串格式
     *
     * @param date
     * @param type 要转换成的字符串格式，如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2String(Date date, String type)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        String time = sdf.format(date);
        return time;
    }
    
    /**
     * 生成一个唯一的UUID
     *
     * @return
     */
    public static String generateUUID()
    {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
    
    /**
     * 生成一个唯一的订单id
     *
     * @return
     */
    public synchronized static String generateOrderNumber()
    {
        try
        {
            Thread.sleep(100);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        Calendar curr = Calendar.getInstance();
        StringBuffer suffix = new StringBuffer(
            ((curr.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000) + (curr.get(Calendar.MINUTE) * 60 * 1000) + (curr.get(Calendar.SECOND) * 1000) + curr.get(Calendar.MILLISECOND))
                + "");
        int size = suffix.length();
        for (int i = 8; i > size; i--)
        {
            suffix.insert(0, "0");
        }
        return CommonUtil.date2String(curr.getTime(), "yyyyMMdd") + suffix.substring(0, 6) + CommonConstant.PLATFORM_IDENTITY_CODE;
    }
    
    /**
     * 获取今日晚场特卖日期
     *
     * @return yyyyMMdd
     */
    public static int getNowSaleDateNight()
    {
        Calendar cl = Calendar.getInstance();
        int currentHour = cl.get(Calendar.HOUR_OF_DAY);
        if (currentHour < CommonConstant.SALE_REFRESH_HOUR_NIGHT)
        {
            cl.add(Calendar.DAY_OF_YEAR, -1);
        }
        return Integer.parseInt(date2String(cl.getTime(), "yyyyMMdd"));
    }
    
    public static String removeIllegalEmoji(String content)
    {
        Pattern emoji = Pattern.compile("[\\x{10000}-\\x{10FFFF}]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher mat = emoji.matcher(content);
        return mat.replaceAll("");
    }
    
    public static String replaceIllegalEmoji(String content)
    {
        Pattern emoji = Pattern.compile("[\\x{10000}-\\x{10FFFF}]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher mat = emoji.matcher(content);
        return mat.replaceAll("?");
    }
    
    /**
     * set to list
     * 
     * @param set
     * @return
     */
    public static <T> List<T> setToList(Set<T> set)
    {
        return new ArrayList<T>(set);
    }
    
    public static String getRemoteIpAddr(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.length() > 15)
        {
            if (ip.indexOf(",") > 0)
            {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }
    
    public static int randomIntCode(int n)
    {
        if (n < 1)
            return 0;
        int factor = (int)Math.pow(10, n - 1);
        return (int)((Math.random() * 9 + 1) * factor);
    }
    
    /**
     * 分割List
     * 
     * @param list
     * @param pageSize
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> list, int pageSize)
    {
        int listSize = list.size();
        int page = (listSize + (pageSize - 1)) / pageSize;// 页数
        List<List<T>> listArray = new ArrayList<List<T>>();// 创建list,用来保存分割后的sublist
        for (int i = 0; i < page; i++)
        {
            // 按照数组大小遍历
            List<T> subList = new ArrayList<T>();
            for (int j = 0; j < listSize; j++)
            {
                int pageIndex = (j + pageSize) / pageSize;// 当前记录的页码(第几页)
                if (pageIndex == (i + 1))
                {
                    // 当前记录的页码等于要放入的页码时
                    subList.add(list.get(j)); // 放入分割后的list(subList)
                }
                if ((j + 1) == ((i + 1) * pageSize))
                {
                    // 当放满一页时退出当前循环
                    break;
                }
            }
            listArray.add(subList);
        }
        return listArray;
    }
    
    /**
     * 将request中的参数包装成对象
     * 
     * @param bean
     * @param request
     */
    public static void wrapParamter2Entity(Object bean, HttpServletRequest request)
    {
        Map<String, Object> properties = new HashMap<String, Object>();
        try
        {
            Enumeration<String> names = request.getParameterNames();
            while (names.hasMoreElements())
            {
                String name = names.nextElement();
                String[] value = request.getParameterValues(name);
                if (value.length <= 1)
                {
                    properties.put(name, value);
                }
                else
                {
                    properties.put(name, Arrays.toString(value).replaceAll("\\[", "").replaceAll("\\]", ""));
                }
                
            }
            BeanUtils.populate(bean, properties);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }
    
    public static <T> String list2String(List<T> list, String separator)
    {
        StringBuffer result = new StringBuffer();
        if (list == null || list.isEmpty())
        {
            return result.toString();
        }
        for (T t : list)
        {
            if (result.length() == 0)
            {
                result.append(t.toString());
            }
            else
            {
                result.append(separator).append(t.toString());
            }
        }
        return result.toString();
    }
    
    /**
     * 转换byte到16进制
     *
     * @param b 要转换的byte
     * @return 16进制格式
     */
    private static String byteToHexString(byte b)
    {
        int n = b;
        if (n < 0)
        {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
    
    /**
     * 转换字节数组为16进制字串
     *
     * @param b 字节数组
     * @return 16进制字串
     */
    public static String byteArrayToHexString(byte[] b)
    {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b)
        {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }
    
    /**
     * MD5编码
     *
     * @param origin 原始字符串
     * @return 经过MD5加密之后的结果
     */
    public static String md5Encode(String origin)
    {
        String resultString = null;
        try
        {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(resultString.getBytes("UTF-8"));
            resultString = byteArrayToHexString(md.digest());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return resultString;
    }
    
    public static String objectToXml(Object bean)
    {
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        xStreamForRequestPostData.alias("xml", bean.getClass());
        return xStreamForRequestPostData.toXML(bean);
    }
    
    /**
     * 生成用户的16位大写md5密钥
     * 
     * @param accountId
     * @return
     */
    public static String generateAccountSign(int accountId)
        throws Exception
    {
        String signSuffix = "JiaGengDuoQian18664573290";
        String plainText = accountId + signSuffix;
        
        String sign = plainText;
        for (int i = 0; i < 3; i++)
        {
            sign = strToMD5(sign);
        }
        return sign;
    }
    
    /**
     * 生成t.cn短链接根据长连接
     *
     * @return
     * @throws IOException
     * @throws HttpException
     */
    public static String generateTCNShortUrl(String longUrl)
        throws HttpException, IOException
    {
        PostMethod post = new PostMethod("http://api.t.sina.com.cn/short_url/shorten.json");
        HttpClient client = new HttpClient();
        NameValuePair[] params = new NameValuePair[2];
        params[0] = new NameValuePair("source", "637873839");
        params[1] = new NameValuePair("url_long", longUrl);
        post.setRequestBody(params);
        client.executeMethod(post);
        InputStream in = post.getResponseBodyAsStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String result = br.readLine();
        JSONArray param = JSON.parseArray(result);
        return param.getJSONObject(0).getString("url_short");
    }
    
    /**
     * 对象转map
     * 
     * @param object
     * @return
     */
    public static Map<String, Object> object2Map(Object object)
        throws IllegalAccessException
    {
        Map<String, Object> resultMap = new HashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field f : fields)
        {
            f.setAccessible(true);
            if (f.get(object) != null && f.get(object) != "")
            {
                resultMap.put(f.getName(), f.get(object));
            }
        }
        return resultMap;
    }
    
    public static JSONObject parseXml(String xml)
        throws Exception
    {
        // 将解析结果存储在HashMap中
        JSONObject jsonObject = new JSONObject();
        
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(new ByteArrayInputStream(xml.getBytes()));
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();
        
        // 遍历所有子节点
        for (Element e : elementList)
        {
            jsonObject.put(e.getName(), e.getText());
        }
        return jsonObject;
    }
    
    /**
     * 获取订单超时截止时间
     * 
     * @param payTime
     * @param timeoutLimitDays
     * @return
     * @throws HttpException
     * @throws IOException
     */
    public static Date getOrderTimeoutTime(Date payTime, int timeoutLimitDays)
        throws Exception
    {
        DateTime timeoutTime = new DateTime(payTime.getTime());
        
        while (CommonConstant.holiday.contains(Integer.valueOf(timeoutTime.toString("yyyyMMdd"))))
        {
            // 如果用户付款当天是节假日，则顺延，直到不是节假日为止
            timeoutTime = timeoutTime.withTimeAtStartOfDay().plusDays(1);
        }
        
        int index = 0;
        while (index < timeoutLimitDays)
        {
            timeoutTime = timeoutTime.plusDays(1);
            if (CommonConstant.holiday.contains(Integer.valueOf(timeoutTime.toString("yyyyMMdd"))))
            {
                continue;
            }
            index++;
        }
        return timeoutTime.toDate();
    }
    
    public static List<Integer> splitToInteger(String str, String separator)
    {
        List<String> vars_ = Splitter.on(separator).omitEmptyStrings().splitToList(str);
        List<Integer> ints = new ArrayList<>(vars_.size());
        for (String var_ : vars_)
        {
            ints.add(Integer.valueOf(var_));
        }
        return ints;
    }
    
    public static String getPinYinStr(String input)
    {
        List<Pinyin> pinyinList = HanLP.convertToPinyinList(input);
        char[] chars = input.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pinyinList.size(); i++)
        {
            Pinyin pinyin = pinyinList.get(i);
            String s = pinyin.getPinyinWithoutTone();
            if ("none".equals(s))
            {
                sb.append(chars[i]);
            }
            else
            {
                sb.append(s);
            }
        }
        return sb.toString();
    }
    
    public static int returnDays(String str)
    {
        String dates = str;
        int month = Integer.parseInt(dates.substring(4, 6));
        int year = Integer.parseInt(dates.substring(0, 4));
        Calendar cal = Calendar.getInstance();
        // 在使用set方法之前,必须先clear一下,否则很多信息会继承自系统当前时间
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);// 7月
        int maxDate = cal.getActualMaximum(Calendar.DATE);
        return maxDate;
        
    }
    
    public static void main(String agrs[])
    {
        String a = date2String(new Date(), "yyyyMMdd");
        System.out.println(a);
        
    }
    
    public static Object get10bitCurrentTimestamp()
    {
        return (int)(System.currentTimeMillis() / 1000);
    }
    
    public synchronized static String generateProudctCode()
    {
        try
        {
            Thread.sleep(100);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        Calendar curr = Calendar.getInstance();
        StringBuffer suffix = new StringBuffer(
            ((curr.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000) + (curr.get(Calendar.MINUTE) * 60 * 1000) + (curr.get(Calendar.SECOND) * 1000) + curr.get(Calendar.MILLISECOND))
                + "");
        int size = suffix.length();
        for (int i = 8; i > size; i--)
        {
            suffix.insert(0, "0");
        }
        return "ZH" + CommonUtil.date2String(curr.getTime(), "yyMMdd") + suffix.substring(0, 6);
    }
    
    public static boolean isValidExpressNumber(String expressNumber)
    {
        String regex = "^[a-z0-9A-Z]+-?[a-z0-9A-Z]+${10,20}";
        return Pattern.matches(regex, expressNumber);
    }
    
    /**
     * 将字符中的多个空格替换为一个空格（包括中文全角空格）
     * 
     * @param str
     * @return
     */
    public static String replaceMoreBlankWithOneBlank(String str)
    {
        if (StringUtils.isBlank(str))
        {
            return "";
        }
        String reg = "[.　*]";// 将中文全角空格替换成一个英文空格
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        str = m.replaceAll(" ");
        reg = "[. *]";// 将英文空格替换成一个英文空格
        p = Pattern.compile(reg);
        m = p.matcher(str);
        str = m.replaceAll(" ").trim();
        return str;
    }
    
    /** 验证字符串时间格式是否符合要求 */
    public static boolean verifyDataFormat(SimpleDateFormat sdf, String date)
    {
        if (StringUtils.isNotBlank(date) && !date.equals("0000-00-00 00:00:00"))
        {
            try
            {
                sdf.parse(date);
            }
            catch (Exception e)
            {
                log.info("|" + date + "|");
                return false;
            }
        }
        else
        {
            return false;
        }
        return true;
    }
    
    /** 获取外网ip */
    public static String getWebIP()
    {
        try
        {
            // 连接网页
            URL url = new URL("http://www.ip138.com/ip2city.asp");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String s = "";
            StringBuffer sb = new StringBuffer("");
            String webContent = "";
            // 读取网页信息
            while ((s = br.readLine()) != null)
            {
                sb.append(s + "\r\n");
            }
            br.close();
            // 网页信息
            webContent = sb.toString();
            int start = webContent.indexOf("[") + 1;
            int end = webContent.indexOf("]");
            // 获取网页中 当前 的 外网IP
            webContent = webContent.substring(start, end);
            return webContent;
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "error open url:" + "http://www.ip138.com/ip2city.asp";
        }
    }
    
    /**
     * 生成指定长度的大写字母字符串 如AAAA
     *
     * @param num
     * @return
     */
    public static String generateRandomLetter(int num)
    {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < num; i++)
        {
            str.append(letter[random.nextInt(24)]);
        }
        return str.toString();
    }
    
    /**
     * 分转为元
     * 
     * @param cent 分
     */
    public static double cent2Yuan(long cent)
    {
        return cent / 100d;
    }
    
    /**
     * 是否包含Emoji表情
     * 
     * @param content
     * @return
     */
    public static boolean isEmoji(String content)
    {
        Pattern pattern = Pattern.compile(".*[\\x{10000}-\\x{10FFFF}].*", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        return pattern.matcher(content).find();
    }
    
}
