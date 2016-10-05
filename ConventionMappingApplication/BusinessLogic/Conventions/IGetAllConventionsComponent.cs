using System.Collections.Generic;
using BusinessObjects;

namespace BusinessLogic.Conventions
{
    public interface IGetAllConventionsComponent
    {
        List<Convention> Execute();
    }
}
