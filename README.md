
# Java JDBC

- JDBC, sorguyu veritabanına bağlamak ve yürütmek için kullanılan bir Java API'sidir. JDBC API, veritabanına bağlanmak için JDBC sürücülerini kullanır.

- Platform bağımsızdır. Veri tabanı yapısından bağımsız olduğu için SQL destekleyen bütün veri tabanlarıyla çalışır.

- Driver yüklenmesi, veritabanı bağlantısı'nın yapılması, sorguların yapılması ve sonuçların getirilmesi aşamalarından oluşur.

## Connection
```code
getConnection(String url, String user, String password);
```

Yukarıda bulunan metod ile veritabanı bağlantısı yapılır. Bu metod return olarak Connection interface'i miras alan bir class döndürür.

Connection interface'inde yer alan metodlar ile veritabanı ile ilgili işlemleri gerçekleştirebileceğimiz tanımlara erişilir. Örnek olarak aşağıdaki metod ile veritabanında bulunan şema bilgilerine ulaşabiliriz.

```code
Connection.getSchema();
```

Diğer tüm veritabanı bilgilerine ulaşabilmek için getMetaData() yöntemi kullanılır.
```code
Connection#getMetaData().getSchemas();
Connection#getMetaData().getTableTypes();
Connection#getMetaData().getTypeInfo();
Connection#getMetaData().getDriverName();
```

Veritabanı bağlantısı yaparken kullanılacak olacak veritabanının driver'ı proje içine eklenmesi gerekir. Bu işlem komut yorumlayıcısına jar dosyasının dahil edilmesi, IDE arayüzünde libraries bölümüne eklenmesi veya Maven gibi paket yöneticilerinin kullanımıyla yapılır.

```xml
<dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>8.0.32</version>
</dependency>
```

Tüm veri tabanı işlemleri bittikten sonra connection.close(); metodu ile bağlantı kapatılır.

```code
Connection.close();
```

# SQL Query

Veritabanı sorgularını gerçekleştirebilmemiz için Statement, PreparedStatement, CallableStatement interface'lerini kullanmalıyız.

## Statement
- Doğrudan veri çekme listeleme işlemleri için uygulanabilir.

  Statement nesnesi oluşturalım.

    ```code
    Statement statement = connection.createStatement();	
    ```
  execute() metodu kullanılarak sql sorguları yazılabilir.

    ```code
    statement.execute("sql-query");
    ```
  Sorgu sonrası sonuçları alabilmek için
    ```code
    statement.getResultSet();
    ```
  Birden fazla toplu sorgu işlemleri yapabilmek için executeBatch kullanılır. Ancak   bu yöntemi kullanabilmek için addBatch() ile metodları eklemek gerekir.
    ```code
    statement.addBatch("query-1");
	statement.addBatch("query-2");
	statement.addBatch("query-3");
	statement.executeBatch();
    ```

## PreparedStatement

- Genellikle filtreleme ***WHERE*** işlemi kullanıldığı zaman yada ***INSERT***, ***UPDATE*** işlemlerinde kullanılır.
- Statement'dan farkı yazılan sql sorguları tekrar tekrar kullanılabilir olmasıdır.

  Kullanılabilecek örnek SQL sorguları

  ```sql
  INSERT INTO student (id, name, school) VALUES (?,?,?)
  ```
  ```sql
  UPDATE student SET name=?, school=? WHERE id=?
  ```
  ```sql
  DELETE FROM student WHERE id = ?
  ```
- Ayrıca SQL sorgularında yer alan metodlara değer atamak için setVeriTipi(setString, setInt) gibi metodlara sahiptir.

  ```code
    String sql = "INSERT INTO kisiler(firstName, lastName) VALUES(?, ?)";

	PreparedStatement preparedStatement = connection.prepareStatement(sql);
	preparedStatement.setString(1, "Emin");
	preparedStatement.setString(2, "YILDIZ");
  ```
- Değerler atandıktan sonra komut türüne göre  execute, executeUpdate veya executeQuery metodları kullanılır.

## CallableStatement

- Saklı yordam metodlarını veritabanına göndermek için çeşitli metodlar tanımlar.
- Saklı yordam  =>  VTYS içerisinde önceden derlenmiş SQL komutlarıdır.

  ```code
  CallableStatement callableStatement = connection.prepareCall("{ CALL SP_KISI_EKLE(? , ?)}");

  callableStatement.setString(1, "Emin");
  callableStatement.setString(2, "YILDIZ");
  callableStatement.executeUpdate();
  ``` 
- Saklı yordam çalıştırıldıktan sonra OUT ile işaretlenen parametreleri almak için paremetre registerOutParameter metodu ile belirtilmelidir.

  ```code
  callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);
  callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);
  callableStatement.registerOutParameter(3, java.sql.Types.DATE);
  ``` 
- 	Parametreler belirtildikten sonra getVeriTipi metotları ile değer alınır.

  ```code
  callableStatement.getString(1);
  callableStatement.getString(2);
  callableStatement.getDate(3);
  ``` 

## ResultSet

- Sorgu sonrası veri listelemek için kullanılır.
- ***next()***, ***first()***, ***last()***, ***previous()***, metodları kullanılarak verileri arasında gezinebiliriz.

  Verileri alalım

    ```code
    ResultSet result = statement.executeQuery("SELECT * FROM kisiler");
    ``` 

  Verileri görüntülemek için
    ```code
    while (result.next()) {
	    sira = result.getInt(1);
	    adi = result.getString(2);
	    soyadi = result.getString("kisi_soyadi");
	    System.out.println(sira + " " + adi + " " + soyadi);
	}
    ``` 







