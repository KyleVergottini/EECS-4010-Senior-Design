namespace DataAccess
{
    using System;
    using System.Data.Entity;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Linq;

    public partial class Entities : DbContext
    {
        public Entities()
            : base("name=Entities")
        {
        }

        public virtual DbSet<ConventionRole> ConventionRoles { get; set; }
        public virtual DbSet<Convention> Conventions { get; set; }
        public virtual DbSet<Event> Events { get; set; }
        public virtual DbSet<Room> Rooms { get; set; }
        public virtual DbSet<Schedule> Schedules { get; set; }
        public virtual DbSet<User> Users { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Convention>()
                .HasMany(e => e.ConventionRoles)
                .WithRequired(e => e.Convention)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Convention>()
                .HasMany(e => e.Rooms)
                .WithRequired(e => e.Convention)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Convention>()
                .HasMany(e => e.Schedules)
                .WithRequired(e => e.Convention)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Event>()
                .HasMany(e => e.Schedules)
                .WithRequired(e => e.Event)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Room>()
                .HasMany(e => e.Events)
                .WithRequired(e => e.Room)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<User>()
                .HasMany(e => e.ConventionRoles)
                .WithRequired(e => e.User)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<User>()
                .HasMany(e => e.Schedules)
                .WithRequired(e => e.User)
                .WillCascadeOnDelete(false);
        }
    }
}
