SELECT s.city, s.store_state, s.store_zip, sum(f.dollar_sales) AS sales_total
FROM Product p, Store s, Date_time t, Sales f
WHERE f.product_key =p.product_key AND f.store_key = s.store_key AND f.time_key = t.time_key AND s.store_state = 'PA'
GROUP BY s.city, s.store_state, s.store_zip;