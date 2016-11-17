namespace API.Models
{
    public class SuccessReturn
    {
        public string success;

        public SuccessReturn(string message)
        {
            this.success = message;
        }
    }
}