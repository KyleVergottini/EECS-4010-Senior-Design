using Map = BusinessObjects.Map;

namespace API.Models
{
    public class MapReturn
    {
        public string ConventionID { get; set; }

        public byte[] MapImage1 { get; set; }

        public byte[] MapImage2 { get; set; }

        public byte[] MapImage3 { get; set; }

        public MapReturn(string conventionID, Map map)
        {
            ConventionID = conventionID;
            MapImage1 = map.MapImage1;
            MapImage2 = map.MapImage2;
            MapImage3 = map.MapImage3;
        }
    }
}