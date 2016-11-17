namespace API.Models
{
    public class ErrorReturn
    {
        public string error;

        public ErrorReturn(string message)
        {
            this.error = message;
        }
    }
}