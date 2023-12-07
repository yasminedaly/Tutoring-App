CREATE TABLE Account (
                    AccountId INTEGER   PRIMARY KEY AUTOINCREMENT,
                    Card_Holder   Text  not null,
                    AccountNumber    INTEGER not null,
                    Balance         real not null,
                    Currency TEXT  not null,
                    AccountType TEXT  not null,
                    cvv integer not null

);
CREATE TABLE "Transaction"(
                             TransactionId INTEGER   PRIMARY KEY AUTOINCREMENT,
                             FromAccountId   INTEGER  not null,
                             ToAccountId     INTEGER  not null,
                             Amount    real not null,
                             TransactionDate        DATE  not null,
                         Currency TEXT  not null

);