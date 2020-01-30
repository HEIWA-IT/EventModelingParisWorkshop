using System;
using System.Collections.Generic;
using System.Text;
using FluentAssertions;
using Infrastructure;
using NSubstitute;
using Xunit;
using Xunit.Abstractions;

namespace Rooms
{
    public class RoomsAvailabilityTest
    {
        private readonly ITestOutputHelper log;
        public RoomsAvailabilityTest(ITestOutputHelper log) => this.log = log;

        [Fact]
        public void WhenIReserve2At1janAndAnotherOneLaterIShouldHave2RoomReservedTheFirstJan()
        {
            var jan1 = new RoomsAvailability { AtDate = new DateTime(2020, 1, 1) };
            var subtitute = Substitute.For<IRoomsAvailabilityAccessor>();
            subtitute.GetAt(Arg.Any<DateTime>()).Returns(x => new RoomsAvailability());
            subtitute.GetAt(Arg.Is(new DateTime(2020, 1, 1))).Returns(jan1);

            var sut = new RoomReservedHandler(subtitute);

            sut.Handle(new RoomReserved { FromDate = new DateTime(2019, 12, 31), ToDate = new DateTime(2020, 1, 3), RoomNumber = "12" });
            subtitute.Received(1).Save(jan1);
            sut.Handle(new RoomReserved { FromDate = new DateTime(2019, 12, 31), ToDate = new DateTime(2020, 1, 2), RoomNumber = "11" });
            subtitute.Received(2).Save(jan1);
            sut.Handle(new RoomReserved { FromDate = new DateTime(2020, 1, 2), ToDate = new DateTime(2020, 1, 3), RoomNumber = "10" });
            subtitute.Received(2).Save(jan1);

            jan1.Rooms.Count.Should().Be(2);
            jan1.Rooms.Should().Contain(x => x.Number == "12");
            jan1.Rooms.Should().Contain(x => x.Number == "11");
        }
    }
}
