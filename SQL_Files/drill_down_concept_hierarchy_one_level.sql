SELECT p.brand, s.city, t.year, sum(f.dollar_sales) AS sales_total
FROM Product p, Store s, Date_time t, Sales f
WHERE f.product_key =p.product_key AND
             f.store_key = s.store_key AND
             f.time_key = t.time_key
GROUP BY p.brand, s.city, t.year;