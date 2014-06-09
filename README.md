# Coding Challenge

## Overview

This program will take in as input a number of investments, and will rebalance those investments to get as close as possible to the target allocations for each investment.

## Input

The first line will contain a number N representing the number of investments. Subsequent lines will contain the details of each investment. Each investment will contain the following information: Ticker, Target allocation, Actual allocation, Shares owned, Share price. Each piece of information will be separated by a single space.

## Sample Input

 3 
 goog 60 50.96 52 98 
 aapl 30 29.92 136 22 
 tsla 10 19.12 239 8

## Output

The procedure to follow for obtaining the optimal allocations

## How it works & reasoning

All allocations will be reset before finding the optimal solution.

* This allows to fully allocate an amount to each investment in one go instead of having to figure out how much to adjust by.

This program will then order the investments by share price from highest to lowest.

Next, funds will be allocated to each company one at a time in order from highest share price to lowest share price.

* It is better to be off a share amount or two for a company of lower share price than off for a company that has a high share price.

The program will then derive the steps required to go from the original allocations to the optimal allocations.

## TODO

1. Restructure the code for deriving how to rebalance the investments.
..* The functions should be shorter, ideally no more than 10 lines of code each.

2. Variable names and function names should be chosen in a way such that the code comments itself.

3. Throw exceptions for invalid data.

..* It is possible that two investments can be provided as input where investment 
'a' yields a total of 'x' dolars in funds to be allocated, and investment 'b' yields 
'y' dolars in funds to be allocated. i.e. for investment 'a', (share price * shares owned) / (actual allocation / 100)
!= the same for investment 'b'. These values must be the same for the actual allocation to be meaningful.
..* An exception should be thrown if an investment is added for a company that has already been invested in.
..* An exception should be thrown if the actual allocation percentages add to over 100%.

4. More test cases

More samples must be created in order to test the following:

..* Multiple investments where the share price is the same.
..* Inconsistent amount of funds to be allocated.
..* A portfolio with a significantly large number of investments (maybe 50 - 100 or even 1k).
..* A case where the target allocation percentages do not add to 100%.
..* A case where the actual allocation percentages add to over 100%.


