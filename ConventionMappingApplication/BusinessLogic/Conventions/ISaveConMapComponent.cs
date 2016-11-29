namespace BusinessLogic.Conventions
{
    public interface ISaveConMapComponent
    {
        bool Execute(int conId, byte[] map);
    }
}
