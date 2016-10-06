using System.Collections.Generic;
using BusinessObjects;

namespace BusinessLogic.Maps
{
    public interface IGetMapForConventionComponent
    {
        List<Map> Execute(int conventionId);
    }
}
