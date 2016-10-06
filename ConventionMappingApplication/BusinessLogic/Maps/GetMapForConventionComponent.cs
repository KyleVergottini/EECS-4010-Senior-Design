using System.Collections.Generic;
using System.Linq;
using DataAccess;
using Convention = BusinessObjects.Convention;
using Room = BusinessObjects.Room;
using Map = BusinessObjects.Map;

namespace BusinessLogic.Maps
{
    public class GetMapForConventionComponent : IGetMapForConventionComponent
    {
        public List<Map> Execute(int conventionId)
        {
            using (var context = new Entities())
            {
                List<Map> mapRecords = context.Conventions.Where(x => x.ID == conventionId)
                    .Select(x => new List<Map>
                    {
                        new Map {
                            ConventionId = x.ID,
                            Level = 1,
                            Map = x.Map1
                        },
                        new Map {
                            ConventionId = x.ID,
                            Level = 2,
                            Map = x.Map2
                        },
                        new Map {
                            ConventionId = x.ID,
                            Level = 3,
                            Map = x.Map3
                        }
                    }).FirstOrDefault()
                    .Where(x => x.Map != null)
                    .ToList();

                foreach (Map mapRecord in mapRecords)
                {
                    if (mapRecord.Map != null)
                    {
                        mapRecord.RoomList = context.Rooms.Where(x => x.Level == mapRecord.Level)
                            .Select(x => new Room
                            {
                                ID = x.ID,
                                ConventionID = x.ID,
                                Name = x.Name,
                                Level = x.Level,
                                XCoordinate = x.XCoordinate,
                                YCoordinate = x.YCoordinate
                            }).ToList();
                    }
                }

                return mapRecords;
            }
        }
    }
}
