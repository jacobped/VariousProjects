import pandas as pd
salaries = pd.read_csv('/home/jacob/Andet/Temp/Big Data (Workshop)/Data/Salaries.csv')
print(salaries.head())
print(salaries.describe())

print('Second data set')

latest_salaries = salaries[salaries['Year'] == 2014]
print(latest_salaries.head())
print(latest_salaries.describe())

print('Third dataset')

average_yearly_rent = 3880 * 12
average_city_pay = latest_salaries['TotalPay'].mean() # Mean is average
print(average_yearly_rent / average_city_pay)

print('How many people earns less than average yearly rent:')
print(latest_salaries[latest_salaries['TotalPay'] < average_yearly_rent].shape[0]) #shape[0] gives an aggregate value with the count of rows

print('How many people work overtime:')

base_pay_series = pd.to_numeric(latest_salaries['BasePay'], errors='coerce')
print(base_pay_series[base_pay_series * .7 < average_yearly_rent].shape[0])

print('How many people work overtime annually:')
overtime_series = pd.to_numeric(latest_salaries['OvertimePay'], errors='coerce')
print(overtime_series[overtime_series > 1000].shape[0])

