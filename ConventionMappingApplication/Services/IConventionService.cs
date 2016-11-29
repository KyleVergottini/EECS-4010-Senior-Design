using System.Collections.Generic;
using BusinessObjects;

namespace Services
{
    public interface IConventionService
    {
        Convention GetConventionById(int conId);

        int SaveConvention(Convention convention);

        List<Convention> GetAllConventions();

        bool SaveMap(int conId, byte[] map);

        ConMap GetMapByConventionId(int conId);
    }
}
