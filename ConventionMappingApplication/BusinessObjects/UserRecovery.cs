using System;

namespace BusinessObjects
{
    public class UserRecovery
    {
        public int UserID { get; set; }

        public string HashedRecoveryCode { get; set; }

        public DateTime ExpirationDate { get; set; }
    }
}
