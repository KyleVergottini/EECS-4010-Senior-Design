﻿using System;

namespace BusinessObjects
{
    public class Event
    {
        public int ID { get; set; }

        public int RoomID { get; set; }

        public string Name { get; set; }

        public DateTime StartDate { get; set; }

        public DateTime EndDate { get; set; }

        public string Description { get; set; }
    }
}